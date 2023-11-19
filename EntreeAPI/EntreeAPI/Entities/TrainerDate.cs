using System;
using System.Collections.Generic;

namespace EntreeAPI.Entities
{
    public partial class TrainerDate
    {
        public int Id { get; set; }
        public int TrainerId { get; set; }
        public DateTime Date { get; set; }

        public virtual Trainer Trainer { get; set; } = null!;
    }
}
