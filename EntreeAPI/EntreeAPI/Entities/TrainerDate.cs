using System.ComponentModel.DataAnnotations;

namespace EntreeAPI.Entities
{
    public class TrainerDate
    {
        [Key]
        public int Id { get; set; }

        public int TrainerId { get; set; }
        public Trainer Trainer { get; set; }

        public DateTime Date { get; set; }
    }
}
