using System;
using System.Collections.Generic;

namespace EntreeAPI.Entities
{
    public partial class TicketType
    {
        public TicketType()
        {
            Tickets = new HashSet<Ticket>();
            TrainerClassTicketTypes = new HashSet<TrainerClassTicketType>();
        }

        public int Id { get; set; }
        public string Name { get; set; } = null!;
        public int Price { get; set; }
        public int SportFascilityId { get; set; }
        public int CategoryId { get; set; }
        public int? MaxUsages { get; set; }
        public int? ValidityDays { get; set; }
        public bool IsDeleted { get; set; }

        public virtual TicketCategory Category { get; set; } = null!;
        public virtual SportFacility SportFascility { get; set; } = null!;
        public virtual ICollection<Ticket> Tickets { get; set; }
        public virtual ICollection<TrainerClassTicketType> TrainerClassTicketTypes { get; set; }
    }
}
