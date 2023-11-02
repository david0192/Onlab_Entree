using System.ComponentModel.DataAnnotations;

namespace EntreeAPI.Models
{
    public class TrainerDTO
    {
        public int Id { get; set; }
        
        [Required]
        public string Name {get; set;}
    }
}
