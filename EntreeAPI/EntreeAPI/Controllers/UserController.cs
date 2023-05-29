using EntreeAPI.Entities;
using Microsoft.AspNetCore.Mvc;
using AutoMapper;
using Microsoft.EntityFrameworkCore;
using EntreeAPI.Models;

namespace EntreeAPI.Controllers
{
    [Route("api/users")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly IMapper _mapper;
        private readonly EntreeDBContext _context;

        public UserController(IMapper mapper, EntreeDBContext context)
        {
            _mapper = mapper;
            _context = context;
        }

        // GET: api/users
        [HttpGet]
        public async Task<ActionResult<IEnumerable<UserDTO>>> GetUsers()
        {
            var users = await _mapper.ProjectTo<UserDTO>(_context.Users).ToListAsync();

            return Ok(users);
        }

        [HttpPost]
        public async Task AddGuestUser(UserDTO userDTO)
        {
            var user= _context.Users.Where(u=>u.Email==userDTO.Email).FirstOrDefault();
            if (user==null)
            {

                var newuser = new User
                {
                    Email = userDTO.Email,
                    Role = userDTO.Role,

                };
  
                 _context.Users.Add(newuser);

                if (userDTO.Role == "Guest")
                {
                    var guest = new Guest
                    {
                        User = newuser
                    };
                    _context.Guests.Add(guest);

                    newuser.Guest = guest;
                }

               

                await _context.SaveChangesAsync();
            }

        }
    }

}
