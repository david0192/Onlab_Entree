using System.ComponentModel.DataAnnotations;

namespace EntreeAPI.Entities
{
    public class TicketType
    {
        [Key]
        public int Id { get; set; }
        [Required]
        public string Name { get; set; }
        [Required]
        public int Price { get; set; }

        public int SportFascilityId { get; set; }

        public SportFacility SportFacility { get; set; }

        public int CategoryId { get; set; }

        public TicketCategory Category { get; set; }
    }
}
