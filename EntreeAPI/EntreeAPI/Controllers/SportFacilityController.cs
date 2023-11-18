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
    public class SportFacilityController : ControllerBase
    {
        private readonly IMapper _mapper;
        private readonly EntreeDBContext _context;

        public SportFacilityController(IMapper mapper, EntreeDBContext context)
        {
            _mapper = mapper;
            _context = context;
        }

        // GET: api/sportfacilities
        [Route("api/sportfacilities")]
        [HttpGet]
        public async Task<ActionResult<IEnumerable<SportFacilityDTO>>> GetSportFacilities()
        {
            var sportfacilities = await _mapper.ProjectTo<SportFacilityDTO>(_context.SportFacilities).ToListAsync();

            return Ok(sportfacilities);
        }

        ///Get:api/sportfacilities/{id}/{catId}
        [Route("api/sportfacilities/{id}/{catId}")]
        [HttpGet]
        public async Task<ActionResult<IEnumerable<TicketTypeDTO>>> GetTicketTypesByIdAndCatId(int id, int catId)
        {
            var tickettypes = await _mapper.ProjectTo<TicketTypeDTO>(_context.TicketTypes.Where(t => t.SportFascilityId == id && t.CategoryId == catId && t.IsDeleted == 0)).ToListAsync();

            return Ok(tickettypes);
        }

        [Route("api/sportfacility/{uid}")]
        [HttpGet]
        public async Task<ActionResult<SportFacilityDetailsDTO>> GetSportFacilityByAdminUid(string uid)
        {
            var user = await _context.Users.Where(u => u.Uid == uid).FirstOrDefaultAsync();

            if (user is not null)
            {
                if (user.RoleId == (int)Roles.Admin)
                {
                    var admin = await _context.Admins.Where(a => a.UserId == user.Id).FirstOrDefaultAsync();

                    if (admin is not null)
                    {
                        var sportFacilityDetailsDTO = await _mapper.ProjectTo<SportFacilityDetailsDTO>(_context.SportFacilities
                        .Where(s => s.Id == admin.SportFacilityId))
                        .FirstOrDefaultAsync();

                        if (sportFacilityDetailsDTO is not null && sportFacilityDetailsDTO.Id != 0)
                        {
                            var ticketTypes = await _mapper.ProjectTo<TicketTypeDTO>(_context.TicketTypes
                            .Where(t => t.SportFascilityId == admin.SportFacilityId && t.IsDeleted == 0))
                            .ToListAsync();

                            if (ticketTypes is not null)
                            {
                                foreach (var ticketType in ticketTypes)
                                {
                                    sportFacilityDetailsDTO.TicketTypes.Add(ticketType);
                                }
                            }

                            return Ok(sportFacilityDetailsDTO);
                        }
                        else
                        {
                            return NoContent();
                        }
                    }
                    else
                    {
                        return NoContent();
                    }
                }
                else
                {
                    return NoContent();
                }
            }
            else
            {
                return NoContent();
            }
        }

        [Route("api/sportfacility")]
        [HttpPost]
        public async Task UpdateSportFacility([FromBody] SportFacilityDTO sportFacilityDto)
        {
            var sportFacility = await _context.SportFacilities.Where(s => s.Id == sportFacilityDto.Id).FirstOrDefaultAsync();

            if (sportFacility is not null)
            {
                sportFacility.Name = sportFacilityDto.Name;
                sportFacility.Site = sportFacilityDto.Site;
                await _context.SaveChangesAsync();
            }
        }

        [Route("api/sportFacilityStatistics")]
        [HttpGet]
        public async Task<ActionResult<SportFacilityStatisticsResultDTO>> GetSportFacilityStatistics(string? uid, DateTime? beginTime, DateTime? endTime)
        {
            var user = await _context.Users.Where(u => u.Uid == uid).FirstOrDefaultAsync();

            if (user is not null)
            {
                if (user.RoleId == (int)Roles.Admin)
                {
                    var admin = await _context.Admins.Where(a => a.UserId == user.Id).FirstOrDefaultAsync();

                    if (admin is not null)
                    {
                        int? sportFacilityId = await _context.SportFacilities
                        .Where(s => s.Id == admin.SportFacilityId).Select(x=>x.Id).FirstOrDefaultAsync();

                        if (sportFacilityId is not null)
                        {
                            var ticketTypes = await _context.TicketTypes.Where(t => t.SportFascilityId == sportFacilityId).ToListAsync();

                            if (ticketTypes is not null)
                            {
                                SportFacilityStatisticsResultDTO sportFacilityStatisticsResultDTO = new SportFacilityStatisticsResultDTO();
                                int revenue = 0;

                                foreach (var ticketType in ticketTypes)
                                {
                                    var numberOfPurchases = 0;
                                    var tickets = await _context.Tickets.Where(t => t.TicketTypeId == ticketType.Id && (t.PurchaseDate <=endTime
                                    && t.PurchaseDate >= beginTime) || (endTime == null && t.PurchaseDate >=beginTime)).ToListAsync();
                                    foreach (var ticket in tickets)
                                    {
                                        revenue += ticketType.Price;
                                        numberOfPurchases++;
                                    }
                                    sportFacilityStatisticsResultDTO.TicketTypeBuyNumbers.Add(ticketType.Name, numberOfPurchases);
                                }
                                sportFacilityStatisticsResultDTO.Revenue = revenue;
                                return sportFacilityStatisticsResultDTO;
                            }
                            else
                            {
                                return Ok(new SportFacilityStatisticsResultDTO());
                            }
                        }
                        else
                        {
                            return Ok(new SportFacilityStatisticsResultDTO());
                        }
                    }
                    else
                    {
                        return Ok(new SportFacilityStatisticsResultDTO());
                    }
                }
                else
                {
                    throw new ArgumentException("Nem Admin!");
                }
            }
            else
            {
                throw new ArgumentException("User Id nem lehet null!");
            }
        }
    }
}
