namespace EntreeAPI.Models
{
    public class TicketTypeDetailsDTO
    {
        public int Id { get; set; }
        public string Name { get; set; }

        public int Price { get; set; }

        public int CategoryId { get; set; }

        public int MaxUsages { get; set; }

        public int ValidityDays { get; set; }

        public Dictionary<int, string> CategoryValues { get; set; } = new Dictionary<int, string>();
    }
}
