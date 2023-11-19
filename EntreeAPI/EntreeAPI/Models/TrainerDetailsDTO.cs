namespace EntreeAPI.Models
{
  public class TrainerDetailsDTO
  {
    public int Id { get; set; }
    public string Name { get; set; } = "";
    public int SportFacilityId { get; set; }
    public string? Introduction { get; set; }
  }
}
