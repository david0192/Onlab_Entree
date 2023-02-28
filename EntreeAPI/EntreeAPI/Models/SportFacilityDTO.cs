using System.ComponentModel.DataAnnotations;

namespace EntreeAPI.Models
{
    public class SportFacilityDTO
    {
        [Key]
        public int Id { get; set; }

        public string Name { get; set; }
        
        [Required]
        public string Site { get; set; }

    }
}
