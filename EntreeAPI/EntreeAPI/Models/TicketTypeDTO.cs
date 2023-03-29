using System.ComponentModel.DataAnnotations;

namespace EntreeAPI.Models
{
    public class TicketTypeDTO
    {
        public int Id { get; set; }
        public string Name { get; set; }    

        public int Price { get; set; }

    }
}
