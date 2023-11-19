using System.ComponentModel.DataAnnotations;

namespace EntreeAPI.Models
{
    public class SportFacilityDetailsDTO
    {
        public int Id { get; set; }

        public string Name { get; set; }

        [Required]
        public string Site { get; set; }

        public List<TicketTypeDTO> TicketTypes { get; set; } = new List<TicketTypeDTO>();
        public List<TrainerDTO> Trainers { get; set; } = new List<TrainerDTO>();
    }
}
