using AutoMapper;
using EntreeAPI.Entities;
using EntreeAPI.Models;

namespace EntreeAPI
{
    public class AutoMapperProfile : Profile
    {
        public AutoMapperProfile()
        {
            CreateMap<User, UserDTO>();
            CreateMap<SportFacility, SportFacilityDTO>();
            CreateMap<SportFacility, SportFacilityDetailsDTO>();
            CreateMap<TicketType, TicketTypeDTO>();
            CreateMap<TicketType, TicketTypeDetailsDTO>();
            CreateMap<Trainer, TrainerDTO>();
            CreateMap<Ticket, TicketDTO>().ForMember(t => t.TypeName, t => t.MapFrom(z => z.TicketType != null ? z.TicketType.Name : null));
        }
    }
}
