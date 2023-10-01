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
                return null;
            }
            else
            {
                if (userquery.Role != Roles.Guest.ToString())
                {
                    return null;
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

                    if (userquery.Role == Roles.Guest.ToString() && guest != null)
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
    }
}
