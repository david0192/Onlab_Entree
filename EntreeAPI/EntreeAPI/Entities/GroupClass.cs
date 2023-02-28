using System.ComponentModel.DataAnnotations;

namespace EntreeAPI.Entities
{
    public class GroupClass
    {
        [Key]
        public int Id { get; set; }
        [Required]
        public string Name { get; set; }

        public ICollection<GroupClassDate> GroupClassDates { get; set; }
        public string Tickettype { get; set; }

        public int SportFacilityId { get; set;}

        public SportFacility SportFacility { get; set; }


    }
}
