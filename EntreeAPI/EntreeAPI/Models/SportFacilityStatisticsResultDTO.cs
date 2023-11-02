namespace EntreeAPI.Models
{
    public class SportFacilityStatisticsResultDTO
    {
        public int Revenue { get; set; }
        public Dictionary<string, int> TicketTypeBuyNumbers { get; set; } = new Dictionary<string, int>();
    }
}
