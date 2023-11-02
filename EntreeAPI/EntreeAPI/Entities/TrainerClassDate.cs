using System;
using System.Collections.Generic;

namespace EntreeAPI.Entities
{
    public partial class TrainerClassDate
    {
        public TrainerClassDate()
        {
            Tickets = new HashSet<Ticket>();
        }

        public int Id { get; set; }
        public int TrainerClassDateId { get; set; }
        public DateTime Date { get; set; }
        public int TrainerId { get; set; }
        public int? Capacity { get; set; }

        public virtual Trainer Trainer { get; set; } = null!;
        public virtual TrainerClass TrainerClassDateNavigation { get; set; } = null!;
        public virtual ICollection<Ticket> Tickets { get; set; }
    }
}
