using AutoMapper;
using EntreeAPI.Entities;
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

        // GET: api/tickettypes/id
        [Route("api/tickettypes/{id}")]
        [HttpGet]
        public async Task<ActionResult<IEnumerable<TicketTypeDTO>>> GetTicketTypeById(int id)
        {
            var tickettypes = await _mapper.ProjectTo<TicketTypeDTO>(_context.TicketTypes.Where(t => t.Id == id)).ToListAsync();

            return Ok(tickettypes);
        }

        [Route("api/tickets/{email}")]
        [HttpGet]
        public async Task<ActionResult<IEnumerable<TicketDTO>>> GetTicketsByEmail(string email)
        {
            var userquery = await _context.Users.Where(u => u.Email == email).Include(u => u.Guest).FirstOrDefaultAsync();

            if (userquery == null)
            {
                return null;
            }
            else
            {
                if (userquery.Role != "Guest")
                {
                    return null;
                }
                else
                {
                    if (userquery.Guest == null)
                    {
                        throw new ArgumentException("Guest nem lehet null!");
                    }
                    else
                    {
                        var guestId = userquery.Guest.Id;
                        var ticketlistquery = await _mapper.ProjectTo<TicketDTO>(_context.Tickets.Where(t => t.GuestId == guestId)).ToListAsync();
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
                var userquery = await _context.Users.Where(u => u.Email == email).Include(u => u.Guest).FirstOrDefaultAsync();

                if (userquery != null)
                {
                    if (userquery.Role == "Guest" && userquery.Guest != null)
                    {
                        var guest = userquery.Guest;
                        Ticket newticket = new Ticket() { TicketTypeId =(int)ticketTypeId, GuestId = guest.Id };
                        var categoryId = await _context.TicketTypes.Where(x => x.Id == ticketTypeId).Select(x => x.CategoryId).FirstOrDefaultAsync();
                        if (categoryId == 2)
                        {
                            var tickets=await _context.Tickets.Where(x=>x.GuestId==guest.Id).Include(x=>x.Type).ToListAsync();
                            var guestsTicketTypes=tickets.Select(x=>x.Type).ToList();
                            foreach(var type in guestsTicketTypes)
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
