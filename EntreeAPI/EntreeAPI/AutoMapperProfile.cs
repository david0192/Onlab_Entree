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
      CreateMap<SportFacility, SportFacilityDetailsDTO>().ForMember(d=>d.TicketTypes, d=>d.MapFrom(s => s.TicketTypes.Where(x=>!x.IsDeleted)))
        .ForMember(d => d.Trainers, d => d.MapFrom(s => s.Trainers.Where(x => !x.IsDeleted)));
      CreateMap<TicketType, TicketTypeDTO>();
      CreateMap<TicketType, TicketTypeDetailsDTO>();
      CreateMap<Trainer, TrainerDTO>();
      CreateMap<Trainer, TrainerDetailsDTO>();
      CreateMap<Ticket, TicketDTO>().ForMember(t => t.TypeName, t => t.MapFrom(z => z.TicketType != null ? z.TicketType.Name : null));
    }
  }
}
