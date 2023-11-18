namespace EntreeAPI.Models
{
  public class SportFacilityStatisticsResultDTO
  {
    public int Revenue { get; set; } = 0;
    public Dictionary<string, int> TicketTypeBuyNumbers { get; set; } = new Dictionary<string, int>();
  }
}
