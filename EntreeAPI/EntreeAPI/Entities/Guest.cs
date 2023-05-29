using System.ComponentModel.DataAnnotations;

namespace EntreeAPI.Entities
{
    public class Guest
    {
        [Key]
        public int Id { get; set; }
        public string Name { get; set; }
        public string Address { get; set; }

        public string IdCardNumber { get; set; }

        ///Todo:Photo eltárolása
        [Required]
        public User User { get; set; }
        [Required]
        public int userId { get; set; }

        public ICollection<Ticket> Tickets { get; set; }
    }
}
