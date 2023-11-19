using System;
using System.Collections.Generic;

namespace EntreeAPI.Entities
{
    public partial class TrainerClass
    {
        public TrainerClass()
        {
            TrainerClassDates = new HashSet<TrainerClassDate>();
            TrainerClassTicketTypes = new HashSet<TrainerClassTicketType>();
        }

        public int Id { get; set; }
        public string Name { get; set; } = null!;
        public int SportFacilityId { get; set; }

        public virtual SportFacility SportFacility { get; set; } = null!;
        public virtual ICollection<TrainerClassDate> TrainerClassDates { get; set; }
        public virtual ICollection<TrainerClassTicketType> TrainerClassTicketTypes { get; set; }
    }
}
