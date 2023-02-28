using AutoMapper;
using EntreeAPI.Entities;
using EntreeAPI.Models;

namespace EntreeAPI
{
    public class AutoMapperProfile: Profile
    {

        public AutoMapperProfile()
        {
            CreateMap<User, UserDTO>();
        }
    }
}
