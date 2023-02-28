using System.ComponentModel.DataAnnotations;

namespace EntreeAPI.Entities
{
    public class SportFacility
    {
        [Key]
        public int Id { get; set; }
        [Required]
        public string Name { get; set; }
        
        [Required]
        public string Site { get; set; }

        public ICollection<TicketType> TicketTypes { get; set; }
        public ICollection<GroupClass> GroupClasses { get; set; }

        public ICollection<Trainer> Trainers { get; set; }

    }
}
