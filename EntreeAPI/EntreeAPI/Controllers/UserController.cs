using EntreeAPI.Entities;
using Microsoft.AspNetCore.Mvc;
using AutoMapper;
using Microsoft.EntityFrameworkCore;
using EntreeAPI.Models;
using EntreeAPI.Enums;

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

        [HttpPost]
        public async Task AddGuestUser(UserDTO userDTO)
        {
            var user = _context.Users.Where(u => u.Email == userDTO.Email).FirstOrDefault();
            if (user == null)
            {
                var newuser = new User
                {
                    Email = userDTO.Email,
                    RoleId = (int)Roles.Guest,
                    Uid=userDTO.Uid,
                };

                _context.Users.Add(newuser);

                var guest = new Guest
                {
                    User = newuser
                };

                _context.Guests.Add(guest);
                await _context.SaveChangesAsync();
            }
        }

        [Route("role/{email}")]
        [HttpGet]
        public async Task<ActionResult<int>> GetRoleIdByEmail(string email)
        {
            var user = await _context.Users.Where(u => u.Email == email).FirstOrDefaultAsync();

            if (user is not null)
            {
                return user.RoleId;
            }
            else
            {
                return 0;
            }
        }
    }
}
