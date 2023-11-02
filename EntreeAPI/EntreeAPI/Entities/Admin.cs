using System;
using System.Collections.Generic;

namespace EntreeAPI.Entities
{
    public partial class Admin
    {
        public int Id { get; set; }
        public string Name { get; set; } = null!;
        public string Address { get; set; } = null!;
        public string IdCardNumber { get; set; } = null!;
        public int UserId { get; set; }
        public int SportFacilityId { get; set; }

        public virtual SportFacility SportFacility { get; set; } = null!;
        public virtual User User { get; set; } = null!;
    }
}
