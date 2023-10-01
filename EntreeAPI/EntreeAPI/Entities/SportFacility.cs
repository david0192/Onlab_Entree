using System;
using System.Collections.Generic;

namespace EntreeAPI.Entities
{
    public partial class SportFacility
    {
        public SportFacility()
        {
            Admins = new HashSet<Admin>();
            GroupClasses = new HashSet<GroupClass>();
            TicketTypes = new HashSet<TicketType>();
            Trainers = new HashSet<Trainer>();
        }

        public int Id { get; set; }
        public string Name { get; set; } = null!;
        public string Site { get; set; } = null!;

        public virtual ICollection<Admin> Admins { get; set; }
        public virtual ICollection<GroupClass> GroupClasses { get; set; }
        public virtual ICollection<TicketType> TicketTypes { get; set; }
        public virtual ICollection<Trainer> Trainers { get; set; }
    }
}
