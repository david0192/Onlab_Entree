using System.ComponentModel.DataAnnotations;

namespace EntreeAPI.Entities
{
    public class Ticket
    {
        [Key]
        public int Id { get; set; }

        public int TicketTypeId { get; set; }
        public TicketType Type { get; set; }


        public int GuestId { get; set; }

        public Guest Guest { get; set; }


    }
}
