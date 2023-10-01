using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace EntreeAPI.Migrations
{
    public partial class UpdateRelationshipBetweenAdminAndSportFacility : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Admins_SportFacilities_SportFacilityId",
                table: "Admins");

            migrationBuilder.DropForeignKey(
                name: "FK_SportFacilities_Admins_AdminId",
                table: "SportFacilities");

            migrationBuilder.DropIndex(
                name: "IX_SportFacilities_AdminId",
                table: "SportFacilities");

            migrationBuilder.DropColumn(
                name: "AdminId",
                table: "SportFacilities");

            migrationBuilder.AlterColumn<int>(
                name: "SportFacilityId",
                table: "Admins",
                type: "int",
                nullable: false,
                defaultValue: 0,
                oldClrType: typeof(int),
                oldType: "int",
                oldNullable: true);

            migrationBuilder.AddForeignKey(
                name: "FK_Admins_SportFacilities_SportFacilityId",
                table: "Admins",
                column: "SportFacilityId",
                principalTable: "SportFacilities",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Admins_SportFacilities_SportFacilityId",
                table: "Admins");

            migrationBuilder.AddColumn<int>(
                name: "AdminId",
                table: "SportFacilities",
                type: "int",
                nullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "SportFacilityId",
                table: "Admins",
                type: "int",
                nullable: true,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.CreateIndex(
                name: "IX_SportFacilities_AdminId",
                table: "SportFacilities",
                column: "AdminId");

            migrationBuilder.AddForeignKey(
                name: "FK_Admins_SportFacilities_SportFacilityId",
                table: "Admins",
                column: "SportFacilityId",
                principalTable: "SportFacilities",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_SportFacilities_Admins_AdminId",
                table: "SportFacilities",
                column: "AdminId",
                principalTable: "Admins",
                principalColumn: "Id");
        }
    }
}
