using System.ComponentModel.DataAnnotations;

namespace EntreeAPI.Entities
{
    public class Trainer
    {
        [Key]
        public int Id { get; set; }
        [Required]
        public string Name {get; set;}

        public ICollection<TrainerDate> TrainerDates { get; set; }

        public int SportFacilityId { get; set; }

        public SportFacility SportFacility { get; set; }


    }
}
