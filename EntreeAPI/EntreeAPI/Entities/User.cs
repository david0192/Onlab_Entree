using System.ComponentModel.DataAnnotations;

namespace EntreeAPI.Entities
{
    public class User
    {
        [Key]
        public int Id { get; set; }
        
        [Required]
        public string Email { get; set; }

        [Required]
        public string Role { get; set; }

        public Guest Guest { get; set; }

    }
}
