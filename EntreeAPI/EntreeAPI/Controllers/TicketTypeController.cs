using AutoMapper;
using EntreeAPI.Entities;
using EntreeAPI.Enums;
using EntreeAPI.Migrations;
using EntreeAPI.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

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

        [Route("api/tickets/{email}")]
        [HttpGet]
        public async Task<ActionResult<IEnumerable<TicketDTO>>> GetTicketsByEmail(string email)
        {
            var userquery = await _context.Users.Where(u => u.Email == email).FirstOrDefaultAsync();

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

        [Route("api/tickettypes/{ticketTypeId}/{email}")]
        [HttpPost]
        public async Task AddTicketToUser(int? ticketTypeId, string email)
        {
            if (ticketTypeId != null)
            {
                var userquery = await _context.Users.Where(u => u.Email == email).FirstOrDefaultAsync();

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

        [Route("api/tickettypesofsportfacility/{email}")]
        [HttpGet]
        public async Task<ActionResult<TicketTypeDTO>> getSportFacilityTicketTypesByAdminEmail(string email)
        {
            var user = await _context.Users.Where(u => u.Email == email).FirstOrDefaultAsync();

            if (user is not null)
            {
                if (user.RoleId == (int)Roles.Admin)
                {
                    var admin = await _context.Admins.Where(a => a.UserId == user.Id).FirstOrDefaultAsync();

                    if (admin is not null)
                    {
                        var ticketTypeDTOList = await _mapper.ProjectTo<TicketTypeDTO>(_context.TicketTypes
                        .Where(t => t.SportFascilityId == admin.SportFacilityId))
                        .ToListAsync();

                        return Ok(ticketTypeDTOList);
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

        [Route("api/tickettypes")]
        [HttpPost]
        public async Task AddTicketTypetoSportFacility([FromBody] TicketTypeAddDTO ticketTypeDto)
        {

        }

        [Route("api/tickettype/{Id}")]
        [HttpGet]
        public async Task<ActionResult<TicketTypeDetailsDTO>> getTicketTypeById(int Id)
        {
            var ticketType = await _mapper.ProjectTo<TicketTypeDetailsDTO>(_context.TicketTypes.Where(x => x.Id == Id)).FirstOrDefaultAsync();

            if (ticketType is not null)
            {
                var categories = await _context.TicketCategories.ToListAsync();
                
                if(categories is not null)
                {
                    foreach(var category in categories)
                    {
                        ticketType.CategoryValues.Add(category.Id, category.Name);
                    }
                }
                return Ok(ticketType);
            }
            else
            {
                return NoContent();
            }
        }
    }
}
