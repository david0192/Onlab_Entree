using AutoMapper;
using EntreeAPI.Entities;
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
            var tickettypes = await _mapper.ProjectTo<TicketTypeDTO>(_context.TicketTypes.Where(t => t.SportFascilityId == id && t.CategoryId == catId)).ToListAsync();

            return Ok(tickettypes);
        }
    }
}
