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
        public async Task<UserDTO> AddGuestUser(UserDTO userDTO)
        {
            var user= _context.Users.Where(u=>u.Email==userDTO.Email).FirstOrDefault();
            if (user!=null)
            {
                return null;
            }
            else
            {

                var newuser = new User
                {
                    Email = userDTO.Email,
                    PasswordHash = userDTO.PasswordHash,
                    Role = userDTO.Role,

                };
                _context.Users.Add(newuser);

                await _context.SaveChangesAsync();

                return userDTO;
            }

        }
    }

}
