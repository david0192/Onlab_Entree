using System;
using System.Collections.Generic;

namespace EntreeAPI.Entities
{
    public partial class GroupClassDate
    {
        public int Id { get; set; }
        public int GroupClassId { get; set; }
        public DateTime Date { get; set; }

        public virtual GroupClass GroupClass { get; set; } = null!;
    }
}
