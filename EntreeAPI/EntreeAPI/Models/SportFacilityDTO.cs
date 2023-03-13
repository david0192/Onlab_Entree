using System.ComponentModel.DataAnnotations;

namespace EntreeAPI.Models
{
    public class SportFacilityDTO
    {
       

        public string Name { get; set; }
        
        [Required]
        public string Site { get; set; }

    }
}
