using System;
using System.Collections.Generic;

namespace EntreeAPI.Entities
{
    public partial class Ticket
    {
        public int Id { get; set; }
        public int TicketTypeId { get; set; }
        public int GuestId { get; set; }
        public DateTime? ExpirationDate { get; set; }

        public virtual Guest Guest { get; set; } = null!;
        public virtual TicketType TicketType { get; set; } = null!;
    }
}
