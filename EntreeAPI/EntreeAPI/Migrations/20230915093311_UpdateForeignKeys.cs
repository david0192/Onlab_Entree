using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace EntreeAPI.Migrations
{
    public partial class UpdateForeignKeys : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "AdminId",
                table: "SportFacilities",
                type: "int",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_SportFacilities_AdminId",
                table: "SportFacilities",
                column: "AdminId");

            migrationBuilder.AddForeignKey(
                name: "FK_SportFacilities_Admins_AdminId",
                table: "SportFacilities",
                column: "AdminId",
                principalTable: "Admins",
                principalColumn: "Id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_SportFacilities_Admins_AdminId",
                table: "SportFacilities");

            migrationBuilder.DropIndex(
                name: "IX_SportFacilities_AdminId",
                table: "SportFacilities");

            migrationBuilder.DropColumn(
                name: "AdminId",
                table: "SportFacilities");
        }
    }
}
