using System.ComponentModel.DataAnnotations;

namespace EntreeAPI.Entities
{
    public class GroupClassDate
    {
        [Key]
        public int Id { get; set; }

        public int GroupClassId { get; set; }
        public GroupClass GroupClass { get; set; }

        public DateTime Date { get; set; }

    }
}
