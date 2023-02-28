using System.ComponentModel.DataAnnotations;

namespace EntreeAPI.Entities
{
    public class Guest
    {
        [Key]
        public int Id { get; set; }
        [Required]
        public string Name { get; set; }
        [Required]
        public string Address { get; set; }

        [Required]
        public string IdCardNumber { get; set; }

        ///Todo:Photo eltárolása
        public User User { get; set; }

        public ICollection<Ticket> Tickets { get; set; }
    }
}
