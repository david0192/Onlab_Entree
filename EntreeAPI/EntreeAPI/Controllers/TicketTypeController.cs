using AutoMapper;
using EntreeAPI.Entities;
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
            var tickettypes = await _mapper.ProjectTo<TicketTypeDTO>(_context.TicketTypes.Where(t=>t.Id==id)).ToListAsync();

            return Ok(tickettypes);
        }

        [Route("api/tickets/{email}")]
        [HttpGet]
        public async Task<ActionResult<IEnumerable<TicketDTO>>> GetTicketsByEmail(string email)
        {
            var userquery = await _context.Users.Where(u => u.Email == email).Include(u=>u.Guest).FirstOrDefaultAsync();

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
                        var ticketlistquery= await _mapper.ProjectTo<TicketDTO>(_context.Tickets.Where(t => t.GuestId == guestId)).ToListAsync();
                        return Ok(ticketlistquery);
                    }
                }
            }
                
        }


    }
}
