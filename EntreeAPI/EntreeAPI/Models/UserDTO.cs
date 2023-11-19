using System.ComponentModel.DataAnnotations;

namespace EntreeAPI.Models
{
    public class UserDTO
    {
        [Required]
        public string Email { get; set; }

        [Required]
        public int RoleId { get; set; }

        [Required]
        public string Uid { get; set; }
    }
}
