using System.ComponentModel.DataAnnotations;

namespace EntreeAPI.Models
{
    public class GroupClassDTO
    {
        [Required]
        public string Name { get; set; }

        public string Tickettype { get; set; }


    }
}
