using Microsoft.EntityFrameworkCore;

namespace EntreeAPI.Entities
{
    public class EntreeDBContext:DbContext
    {
        public DbSet<Guest> Guests { get; set; }
        public DbSet<User> Users { get; set; }

        public DbSet<SportFacility> SportFacilities { get; set; }

        public DbSet<Ticket> Tickets { get; set; }
        public DbSet<TicketType> TicketTypes { get; set; }
       
        public DbSet<GroupClass> GroupClasses { get; set; }
        public DbSet<GroupClassDate> GroupClassDates { get; set; }

        public DbSet<Trainer> Trainers { get; set; }
        public DbSet<TrainerDate> TrainerDates { get; set; }
        public DbSet<Employee> Employees { get; set; }
        public DbSet<Admin> Admins { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseSqlServer(@"Data Source=(localdb)\MSSQLLocalDB;Initial Catalog=Entree;Integrated Security=True;Connect Timeout=30;Encrypt=False;Trust Server Certificate=False;Application Intent=ReadWrite;Multi Subnet Failover=False");
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
            modelBuilder.Entity<Guest>().HasOne(g => g.User);
            modelBuilder.Entity<Employee>().HasOne(e => e.User);
            modelBuilder.Entity<Admin>().HasOne(a => a.User);
            modelBuilder.Entity<TicketType>().HasOne(t => t.SportFacility).WithMany(s => s.TicketTypes).HasForeignKey(t => t.SportFascilityId);
            modelBuilder.Entity<Ticket>().HasOne(t => t.Guest).WithMany(g => g.Tickets).HasForeignKey(t => t.GuestId);
            modelBuilder.Entity<GroupClassDate>().HasOne(g => g.GroupClass).WithMany(g => g.GroupClassDates).HasForeignKey(g => g.GroupClassId);
            modelBuilder.Entity<GroupClass>().HasOne(g => g.SportFacility).WithMany(s => s.GroupClasses).HasForeignKey(g => g.SportFacilityId);
            modelBuilder.Entity<TrainerDate>().HasOne(t => t.Trainer).WithMany(t => t.TrainerDates).HasForeignKey(t => t.TrainerId);
            modelBuilder.Entity<Trainer>().HasOne(t => t.SportFacility).WithMany(s => s.Trainers).HasForeignKey(t => t.SportFacilityId);
            modelBuilder.Entity<TicketType>().HasOne(t => t.Category);
            modelBuilder.Entity<User>().HasOne(u => u.Guest);

            modelBuilder.Entity<Admin>(entity =>
            {
                entity.Property(e => e.Name).HasMaxLength(50);
                entity.Property(e=>e.Address).HasMaxLength(50);
                entity.Property(e=>e.IdCardNumber).HasMaxLength(50);
                
            });

            modelBuilder.Entity<Employee>(entity =>
            {
                entity.Property(e => e.Name).HasMaxLength(50);
                entity.Property(e => e.Address).HasMaxLength(50);
                entity.Property(e => e.IdCardNumber).HasMaxLength(50);

            });

            modelBuilder.Entity<GroupClass>(entity =>
            {
                entity.Property(e => e.Name).HasMaxLength(50);
                entity.Property(e => e.Tickettype).HasMaxLength(50);

            });
            
            modelBuilder.Entity<Guest>(entity =>
            {
                entity.Property(e => e.Name).HasMaxLength(50);
                entity.Property(e => e.Address).HasMaxLength(50);
                entity.Property(e => e.IdCardNumber).HasMaxLength(50);

            });

            modelBuilder.Entity<SportFacility>(entity =>
            {
                entity.Property(e => e.Name).HasMaxLength(50);
                entity.Property(e => e.Site).HasMaxLength(50);

            });

            modelBuilder.Entity<TicketType>(entity =>
            {
                entity.Property(e => e.Name).HasMaxLength(50);
                entity.Property(e => e.Price).HasMaxLength(50);

            });

            modelBuilder.Entity<Trainer>(entity =>
            {
                entity.Property(e => e.Name).HasMaxLength(50);
               

            });

            modelBuilder.Entity<User>(entity =>
            {
                entity.Property(e => e.Email).HasMaxLength(50);
                entity.Property(e => e.Role).HasMaxLength(50);

            });


        }




    }
}
