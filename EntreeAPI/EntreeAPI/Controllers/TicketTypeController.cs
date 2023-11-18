using AutoMapper;
using EntreeAPI.Entities;
using EntreeAPI.Enums;
using EntreeAPI.Migrations;
using EntreeAPI.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Stripe;
using System.Xml.Linq;

namespace EntreeAPI.Controllers
{

  [ApiController]
  public class TicketTypeController : ControllerBase
  {

    private readonly IMapper _mapper;
    private readonly EntreeDBContext _context;

    public TicketTypeController(IMapper mapper, EntreeDBContext context)
    {
      _mapper = mapper;
      _context = context;
    }

    [Route("api/tickets/{uid}")]
    [HttpGet]
    public async Task<ActionResult<IEnumerable<TicketDTO>>> GetTicketsByUid(string uid)
    {
      var userquery = await _context.Users.Where(u => u.Uid == uid).FirstOrDefaultAsync();

      if (userquery == null)
      {
        return Ok(null);
      }
      else
      {
        if (userquery.RoleId != (int)Roles.Guest)
        {
          return Ok(null);
        }
        else
        {
          var guest = await _context.Guests.Where(g => g.UserId == userquery.Id).FirstOrDefaultAsync();
          if (guest == null)
          {
            throw new ArgumentException("Guest nem lehet null!");
          }
          else
          {
            var guestId = guest.Id;
            var ticketlistquery = await _mapper.ProjectTo<TicketDTO>(_context.Tickets.Include(t => t.TicketType).Where(t => t.GuestId == guestId)).ToListAsync();
            return Ok(ticketlistquery);
          }
        }
      }

    }

    [Route("api/tickettypes/{ticketTypeId}/{uid}")]
    [HttpPost]
    public async Task AddTicketToUser(int? ticketTypeId, string uid)
    {
      if (ticketTypeId != null)
      {
        var userquery = await _context.Users.Where(u => u.Uid == uid).FirstOrDefaultAsync();

        if (userquery != null)
        {
          var guest = await _context.Guests.Where(g => g.UserId == userquery.Id).FirstOrDefaultAsync();

          if (userquery.RoleId == (int)Roles.Guest && guest != null)
          {
            Ticket newticket = new Ticket() { TicketTypeId = (int)ticketTypeId, GuestId = guest.Id };
            var categoryId = await _context.TicketTypes.Where(x => x.Id == ticketTypeId).Select(x => x.CategoryId).FirstOrDefaultAsync();
            if (categoryId == 2)
            {
              var tickets = await _context.Tickets.Where(x => x.GuestId == guest.Id).Include(x => x.TicketType).ToListAsync();
              var guestsTicketTypes = tickets.Select(x => x.TicketType).ToList();
              foreach (var type in guestsTicketTypes)
              {
                if (type.CategoryId == 2)
                {
                  return;
                }
              }

              newticket.ExpirationDate = DateTime.Now.AddDays(30);
            }

            await _context.Tickets.AddAsync(newticket);
            await _context.SaveChangesAsync();
          }
        }
      }
    }

    [Route("api/tickettype/{Id}/{uid}")]
    [HttpGet]
    public async Task<ActionResult<TicketTypeDetailsDTO>> GetTicketTypeById(int Id, string uid)
    {
      var ticketType = await _mapper.ProjectTo<TicketTypeDetailsDTO>(_context.TicketTypes.Where(x => x.Id == Id)).FirstOrDefaultAsync();

      if (ticketType is not null)
      {
        var categories = await _context.TicketCategories.ToListAsync();

        if (categories is not null)
        {
          foreach (var category in categories)
          {
            ticketType.CategoryValues.Add(category.Id, category.Name);
          }
        }
        return Ok(ticketType);
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

        if(sportFacilityId == 0)
        {
          throw new ArgumentException("Edzőteremhez kell tartoznia az Admin felhasználónak!");
        }
        else
        {
          var ticketTypeEmpty = new TicketTypeDetailsDTO();
          var categories = await _context.TicketCategories.ToListAsync();

          if (categories is not null)
          {
            foreach (var category in categories)
            {
              ticketTypeEmpty.SportFacilityId = sportFacilityId;
              ticketTypeEmpty.CategoryValues.Add(category.Id, category.Name);
            }
          }
          return Ok(ticketTypeEmpty);
        }
      }
    }

    [Route("api/ticketType")]
    [HttpPost]
    public async Task CreateOrEditTicketType([FromBody] TicketTypeDetailsDTO ticketTypeDTO)
    {
      if (ticketTypeDTO is not null)
      {
        if (ticketTypeDTO.Id == 0)
        {
          _context.TicketTypes.Add(new TicketType()
          {
            Name = ticketTypeDTO.Name,
            Price = ticketTypeDTO.Price,
            CategoryId = ticketTypeDTO.CategoryId,
            MaxUsages = ticketTypeDTO.MaxUsages,
            ValidityDays = ticketTypeDTO.ValidityDays,
            SportFascilityId = ticketTypeDTO.SportFacilityId,
            IsDeleted = 0
          });

          await _context.SaveChangesAsync();
        }
        else
        {
          var ticketType = await _context.TicketTypes.Where(x => x.Id == ticketTypeDTO.Id).FirstOrDefaultAsync();

          if (ticketType is not null)
          {
            ticketType.Name = ticketTypeDTO.Name;
            ticketType.Price = ticketTypeDTO.Price;
            ticketType.CategoryId = ticketTypeDTO.CategoryId;
            ticketType.MaxUsages = ticketTypeDTO.MaxUsages;
            ticketType.ValidityDays = ticketTypeDTO.ValidityDays;

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

    [Route("api/ticketType/{id}")]
    [HttpDelete]
    public async Task DeleteTicketType(int id)
    {
      var ticketType = await _context.TicketTypes.Where(x => x.Id == id).FirstOrDefaultAsync();
      if (ticketType is not null)
      {
        ticketType.IsDeleted = 1;
        await _context.SaveChangesAsync();
      }
    }
  }
}
