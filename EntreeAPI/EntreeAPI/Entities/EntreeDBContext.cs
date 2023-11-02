using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace EntreeAPI.Entities
{
    public partial class EntreeDBContext : DbContext
    {
        public EntreeDBContext()
        {
        }

        public EntreeDBContext(DbContextOptions<EntreeDBContext> options)
            : base(options)
        {
        }

        public virtual DbSet<Admin> Admins { get; set; } = null!;
        public virtual DbSet<Employee> Employees { get; set; } = null!;
        public virtual DbSet<Guest> Guests { get; set; } = null!;
        public virtual DbSet<Role> Roles { get; set; } = null!;
        public virtual DbSet<SportFacility> SportFacilities { get; set; } = null!;
        public virtual DbSet<Ticket> Tickets { get; set; } = null!;
        public virtual DbSet<TicketCategory> TicketCategories { get; set; } = null!;
        public virtual DbSet<TicketType> TicketTypes { get; set; } = null!;
        public virtual DbSet<Trainer> Trainers { get; set; } = null!;
        public virtual DbSet<TrainerClass> TrainerClasses { get; set; } = null!;
        public virtual DbSet<TrainerClassDate> TrainerClassDates { get; set; } = null!;
        public virtual DbSet<TrainerClassTicketType> TrainerClassTicketTypes { get; set; } = null!;
        public virtual DbSet<User> Users { get; set; } = null!;

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
                optionsBuilder.UseSqlServer("Data Source=(localdb)\\MSSQLLocalDB;Initial Catalog=Entree;Integrated Security=True;Connect Timeout=30;Encrypt=False;Trust Server Certificate=False;Application Intent=ReadWrite;Multi Subnet Failover=False");
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Admin>(entity =>
            {
                entity.HasIndex(e => e.SportFacilityId, "IX_Admins_SportFacilityId");

                entity.HasIndex(e => e.UserId, "IX_Admins_UserId");

                entity.Property(e => e.Address).HasMaxLength(50);

                entity.Property(e => e.IdCardNumber).HasMaxLength(50);

                entity.Property(e => e.Name).HasMaxLength(50);

                entity.HasOne(d => d.SportFacility)
                    .WithMany(p => p.Admins)
                    .HasForeignKey(d => d.SportFacilityId);

                entity.HasOne(d => d.User)
                    .WithMany(p => p.Admins)
                    .HasForeignKey(d => d.UserId);
            });

            modelBuilder.Entity<Employee>(entity =>
            {
                entity.HasIndex(e => e.UserId, "IX_Employees_UserId");

                entity.Property(e => e.Address).HasMaxLength(50);

                entity.Property(e => e.IdCardNumber).HasMaxLength(50);

                entity.Property(e => e.Name).HasMaxLength(50);

                entity.HasOne(d => d.User)
                    .WithMany(p => p.Employees)
                    .HasForeignKey(d => d.UserId);
            });

            modelBuilder.Entity<Guest>(entity =>
            {
                entity.HasIndex(e => e.UserId, "IX_Guests_UserId");

                entity.Property(e => e.Address).HasMaxLength(50);

                entity.Property(e => e.IdCardNumber).HasMaxLength(50);

                entity.Property(e => e.Name).HasMaxLength(50);

                entity.HasOne(d => d.User)
                    .WithMany(p => p.Guests)
                    .HasForeignKey(d => d.UserId);
            });

            modelBuilder.Entity<Role>(entity =>
            {
                entity.Property(e => e.RoleName)
                    .HasMaxLength(255)
                    .IsUnicode(false);
            });

            modelBuilder.Entity<SportFacility>(entity =>
            {
                entity.Property(e => e.Name).HasMaxLength(50);

                entity.Property(e => e.Site).HasMaxLength(50);
            });

            modelBuilder.Entity<Ticket>(entity =>
            {
                entity.HasIndex(e => e.GuestId, "IX_Tickets_GuestId");

                entity.HasIndex(e => e.TicketTypeId, "IX_Tickets_TicketTypeId");

                entity.HasOne(d => d.Guest)
                    .WithMany(p => p.Tickets)
                    .HasForeignKey(d => d.GuestId);

                entity.HasOne(d => d.TicketType)
                    .WithMany(p => p.Tickets)
                    .HasForeignKey(d => d.TicketTypeId);

                entity.HasOne(d => d.TrainerClassDate)
                    .WithMany(p => p.Tickets)
                    .HasForeignKey(d => d.TrainerClassDateId)
                    .HasConstraintName("FK_Tickets_TrainerClassDateId");
            });

            modelBuilder.Entity<TicketCategory>(entity =>
            {
                entity.ToTable("TicketCategory");
            });

            modelBuilder.Entity<TicketType>(entity =>
            {
                entity.HasIndex(e => e.CategoryId, "IX_TicketTypes_CategoryId");

                entity.HasIndex(e => e.SportFascilityId, "IX_TicketTypes_SportFascilityId");

                entity.Property(e => e.Name).HasMaxLength(50);

                entity.HasOne(d => d.Category)
                    .WithMany(p => p.TicketTypes)
                    .HasForeignKey(d => d.CategoryId);

                entity.HasOne(d => d.SportFascility)
                    .WithMany(p => p.TicketTypes)
                    .HasForeignKey(d => d.SportFascilityId);
            });

            modelBuilder.Entity<Trainer>(entity =>
            {
                entity.HasIndex(e => e.SportFacilityId, "IX_Trainers_SportFacilityId");

                entity.Property(e => e.Name).HasMaxLength(50);

                entity.HasOne(d => d.SportFacility)
                    .WithMany(p => p.Trainers)
                    .HasForeignKey(d => d.SportFacilityId);
            });

            modelBuilder.Entity<TrainerClass>(entity =>
            {
                entity.HasIndex(e => e.SportFacilityId, "IX_GroupClasses_SportFacilityId");

                entity.Property(e => e.Name).HasMaxLength(50);

                entity.HasOne(d => d.SportFacility)
                    .WithMany(p => p.TrainerClasses)
                    .HasForeignKey(d => d.SportFacilityId);
            });

            modelBuilder.Entity<TrainerClassDate>(entity =>
            {
                entity.HasIndex(e => e.TrainerClassDateId, "IX_GroupClassDates_GroupClassId");

                entity.HasOne(d => d.TrainerClassDateNavigation)
                    .WithMany(p => p.TrainerClassDates)
                    .HasForeignKey(d => d.TrainerClassDateId)
                    .HasConstraintName("FK_TrainerClassDates_TrainerClasses_TrainerClassId");

                entity.HasOne(d => d.Trainer)
                    .WithMany(p => p.TrainerClassDates)
                    .HasForeignKey(d => d.TrainerId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK__TrainerClassDates_Trainers_TrainerId");
            });

            modelBuilder.Entity<TrainerClassTicketType>(entity =>
            {
                entity.HasKey(e => new { e.TrainerClassId, e.TicketTypeId });

                entity.Property(e => e.GroupClassTicketTypeId).ValueGeneratedOnAdd();

                entity.HasOne(d => d.TicketType)
                    .WithMany(p => p.TrainerClassTicketTypes)
                    .HasForeignKey(d => d.TicketTypeId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_TicketTypeId");

                entity.HasOne(d => d.TrainerClass)
                    .WithMany(p => p.TrainerClassTicketTypes)
                    .HasForeignKey(d => d.TrainerClassId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_TrainerClassId");
            });

            modelBuilder.Entity<User>(entity =>
            {
                entity.HasIndex(e => e.Email, "UQ_Email")
                    .IsUnique();

                entity.HasIndex(e => e.Email, "UQ__Users__A9D10534EA42E69A")
                    .IsUnique();

                entity.Property(e => e.Email).HasMaxLength(50);

                entity.Property(e => e.RoleId).HasDefaultValueSql("((1))");

                entity.HasOne(d => d.Role)
                    .WithMany(p => p.Users)
                    .HasForeignKey(d => d.RoleId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_User_Role");
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
