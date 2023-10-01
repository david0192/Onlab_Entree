using System;
using System.Collections.Generic;

namespace EntreeAPI.Entities
{
    public partial class Trainer
    {
        public Trainer()
        {
            TrainerDates = new HashSet<TrainerDate>();
        }

        public int Id { get; set; }
        public string Name { get; set; } = null!;
        public int SportFacilityId { get; set; }

        public virtual SportFacility SportFacility { get; set; } = null!;
        public virtual ICollection<TrainerDate> TrainerDates { get; set; }
    }
}
