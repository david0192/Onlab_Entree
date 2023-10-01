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
        public virtual DbSet<GroupClass> GroupClasses { get; set; } = null!;
        public virtual DbSet<GroupClassDate> GroupClassDates { get; set; } = null!;
        public virtual DbSet<Guest> Guests { get; set; } = null!;
        public virtual DbSet<SportFacility> SportFacilities { get; set; } = null!;
        public virtual DbSet<Ticket> Tickets { get; set; } = null!;
        public virtual DbSet<TicketCategory> TicketCategories { get; set; } = null!;
        public virtual DbSet<TicketType> TicketTypes { get; set; } = null!;
        public virtual DbSet<Trainer> Trainers { get; set; } = null!;
        public virtual DbSet<TrainerDate> TrainerDates { get; set; } = null!;
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

            modelBuilder.Entity<GroupClass>(entity =>
            {
                entity.HasIndex(e => e.SportFacilityId, "IX_GroupClasses_SportFacilityId");

                entity.Property(e => e.Name).HasMaxLength(50);

                entity.Property(e => e.Tickettype).HasMaxLength(50);

                entity.HasOne(d => d.SportFacility)
                    .WithMany(p => p.GroupClasses)
                    .HasForeignKey(d => d.SportFacilityId);
            });

            modelBuilder.Entity<GroupClassDate>(entity =>
            {
                entity.HasIndex(e => e.GroupClassId, "IX_GroupClassDates_GroupClassId");

                entity.HasOne(d => d.GroupClass)
                    .WithMany(p => p.GroupClassDates)
                    .HasForeignKey(d => d.GroupClassId);
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

            modelBuilder.Entity<TrainerDate>(entity =>
            {
                entity.HasIndex(e => e.TrainerId, "IX_TrainerDates_TrainerId");

                entity.HasOne(d => d.Trainer)
                    .WithMany(p => p.TrainerDates)
                    .HasForeignKey(d => d.TrainerId);
            });

            modelBuilder.Entity<User>(entity =>
            {
                entity.HasIndex(e => e.Email, "UQ__Users__A9D10534EA42E69A")
                    .IsUnique();

                entity.Property(e => e.Email).HasMaxLength(50);

                entity.Property(e => e.Role).HasMaxLength(50);
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
