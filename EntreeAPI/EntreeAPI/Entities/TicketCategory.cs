using System.ComponentModel.DataAnnotations;

namespace EntreeAPI.Entities
{
    public class TicketCategory
    {

        [Key]
        public int Id { get; set; }
        [Required]
        public string Name { get; set; }
    }
}
