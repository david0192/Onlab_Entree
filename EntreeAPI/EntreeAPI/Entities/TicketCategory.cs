using System;
using System.Collections.Generic;

namespace EntreeAPI.Entities
{
    public partial class TicketCategory
    {
        public TicketCategory()
        {
            TicketTypes = new HashSet<TicketType>();
        }

        public int Id { get; set; }
        public string Name { get; set; } = null!;

        public virtual ICollection<TicketType> TicketTypes { get; set; }
    }
}
