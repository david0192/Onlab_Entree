using System;
using System.Collections.Generic;

namespace EntreeAPI.Entities
{
    public partial class User
    {
        public User()
        {
            Admins = new HashSet<Admin>();
            Employees = new HashSet<Employee>();
            Guests = new HashSet<Guest>();
        }

        public int Id { get; set; }
        public string Email { get; set; } = null!;
        public int RoleId { get; set; }

        public virtual Role Role { get; set; } = null!;
        public virtual ICollection<Admin> Admins { get; set; }
        public virtual ICollection<Employee> Employees { get; set; }
        public virtual ICollection<Guest> Guests { get; set; }
    }
}
