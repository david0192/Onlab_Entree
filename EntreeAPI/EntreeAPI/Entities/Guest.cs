using System;
using System.Collections.Generic;

namespace EntreeAPI.Entities
{
    public partial class Guest
    {
        public Guest()
        {
            Tickets = new HashSet<Ticket>();
        }

        public int Id { get; set; }
        public string? Name { get; set; }
        public string? Address { get; set; }
        public string? IdCardNumber { get; set; }
        public int UserId { get; set; }

        public virtual User User { get; set; } = null!;
        public virtual ICollection<Ticket> Tickets { get; set; }
    }
}
