using System;
using System.Collections.Generic;

namespace EntreeAPI.Entities
{
    public partial class GroupClass
    {
        public GroupClass()
        {
            GroupClassDates = new HashSet<GroupClassDate>();
        }

        public int Id { get; set; }
        public string Name { get; set; } = null!;
        public string Tickettype { get; set; } = null!;
        public int SportFacilityId { get; set; }

        public virtual SportFacility SportFacility { get; set; } = null!;
        public virtual ICollection<GroupClassDate> GroupClassDates { get; set; }
    }
}
