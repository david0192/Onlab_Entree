using System;
using System.Collections.Generic;

namespace EntreeAPI.Entities
{
    public partial class Trainer
    {
        public Trainer()
        {
            TrainerClassDates = new HashSet<TrainerClassDate>();
        }

        public int Id { get; set; }
        public string Name { get; set; } = null!;
        public int SportFacilityId { get; set; }
        public string? Introduction { get; set; }

        public virtual SportFacility SportFacility { get; set; } = null!;
        public virtual ICollection<TrainerClassDate> TrainerClassDates { get; set; }
    }
}
