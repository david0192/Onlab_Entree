using System;
using System.Collections.Generic;

namespace EntreeAPI.Entities
{
    public partial class TrainerClassTicketType
    {
        public int GroupClassTicketTypeId { get; set; }
        public int TrainerClassId { get; set; }
        public int TicketTypeId { get; set; }

        public virtual TicketType TicketType { get; set; } = null!;
        public virtual TrainerClass TrainerClass { get; set; } = null!;
    }
}
