using AutoMapper;
using EntreeAPI.Entities;
using EntreeAPI.Enums;
using EntreeAPI.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace EntreeAPI.Controllers
{

  [ApiController]
  public class TrainerController : ControllerBase
  {
    private readonly IMapper _mapper;
    private readonly EntreeDBContext _context;

    public TrainerController(IMapper mapper, EntreeDBContext context)
    {
      _mapper = mapper;
      _context = context;
    }

    [Route("api/trainer/{Id}/{uid}")]
    [HttpGet]
    public async Task<ActionResult<TrainerDetailsDTO>> GetTrainerById(int Id, string uid)
    {
      var trainer = await _mapper.ProjectTo<TrainerDetailsDTO>(_context.Trainers.Where(x => x.Id == Id)).FirstOrDefaultAsync();

      if (trainer is not null)
      {
        return Ok(trainer);
      }
      else
      {
        var user = await _context.Users.Where(u => u.Uid == uid).FirstOrDefaultAsync();

        var sportFacilityId = 0;

        if (user is not null)
        {
          if (user.RoleId == (int)Roles.Admin)
          {
            var admin = await _context.Admins.Where(a => a.UserId == user.Id).FirstOrDefaultAsync();

            if (admin is not null)
            {
              sportFacilityId = admin.SportFacilityId;
            }
          }
        }

        if (sportFacilityId == 0)
        {
          throw new ArgumentException("Edzőteremhez kell tartoznia az Admin felhasználónak!");
        }
        else
        {
          var trainerEmpty = new TrainerDetailsDTO();
          trainerEmpty.SportFacilityId = sportFacilityId;

          return Ok(trainerEmpty);
        }
      }
    }

    [Route("api/trainer")]
    [HttpPost]
    public async Task CreateOrEditTrainer([FromBody] TrainerDetailsDTO trainerDTO)
    {
      if (trainerDTO is not null)
      {
        if (trainerDTO.Id == 0)
        {
          _context.Trainers.Add(new Trainer()
          {
            Name = trainerDTO.Name,
            Introduction = trainerDTO.Introduction,
            SportFacilityId = trainerDTO.SportFacilityId,
            IsDeleted = false
          });

          await _context.SaveChangesAsync();
        }
        else
        {
          var trainer = await _context.Trainers.Where(x => x.Id == trainerDTO.Id).FirstOrDefaultAsync();

          if (trainer is not null)
          {
            trainer.Name = trainerDTO.Name;
            trainer.Introduction = trainerDTO.Introduction;

            await _context.SaveChangesAsync();
          }
          else
          {
            throw new ArgumentException("Nem létezik a TicketType, amit fel szeretne venni!");
          }
        }
      }
      else
      {
        throw new ArgumentException("TicketTypeDTO nem lehet null!");
      }
    }

    [Route("api/trainer/{id}")]
    [HttpDelete]
    public async Task DeleteTrainer(int id)
    {
      var trainer = await _context.Trainers.Where(x => x.Id == id).FirstOrDefaultAsync();
      if (trainer is not null)
      {
        trainer.IsDeleted = true;
        await _context.SaveChangesAsync();
      }
    }

  }
}
