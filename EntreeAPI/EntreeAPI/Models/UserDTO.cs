using System.ComponentModel.DataAnnotations;

namespace EntreeAPI.Models
{
    public class UserDTO
    {
        [Required]
        public string Email { get; set; }

        [Required]
        public string PasswordHash { get; set; }

        [Required]
        public string Role { get; set; }
    }
}
